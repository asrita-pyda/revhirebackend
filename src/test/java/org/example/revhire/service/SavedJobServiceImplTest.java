package org.example.revhire.service;

import org.example.revhire.dto.response.JobResponse;
import org.example.revhire.model.Job;
import org.example.revhire.model.SavedJob;
import org.example.revhire.model.User;
import org.example.revhire.repository.JobRepository;
import org.example.revhire.repository.SavedJobRepository;
import org.example.revhire.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SavedJobServiceImplTest {

    @Mock
    private SavedJobRepository savedJobRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private JobRepository jobRepository;

    @InjectMocks
    private SavedJobServiceImpl savedJobService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveJob_Success() {
        User user = new User();
        user.setId(1L);

        Job job = new Job();
        job.setId(10L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(jobRepository.findById(10L)).thenReturn(Optional.of(job));
        when(savedJobRepository.existsByUserIdAndJobId(1L, 10L)).thenReturn(false);

        savedJobService.saveJob(1L, 10L);

        verify(savedJobRepository).save(any(SavedJob.class));
    }

    @Test
    void unsaveJob_Success() {
        SavedJob savedJob = new SavedJob();
        when(savedJobRepository.findByUserIdAndJobId(1L, 10L)).thenReturn(Optional.of(savedJob));

        savedJobService.unsaveJob(1L, 10L);

        verify(savedJobRepository).delete(savedJob);
    }

    @Test
    void getSavedJobs_Success() {
        Job job = new Job();
        job.setId(10L);
        User employer = new User();
        employer.setId(2L);
        employer.setName("Test Employer");
        job.setEmployer(employer);

        SavedJob savedJob = new SavedJob();
        savedJob.setJob(job);

        when(savedJobRepository.findByUserId(1L)).thenReturn(List.of(savedJob));

        List<JobResponse> responses = savedJobService.getSavedJobs(1L);

        assertNotNull(responses);
        assertFalse(responses.isEmpty());
        assertEquals(10L, responses.get(0).getId());
    }

    @Test
    void saveJob_AlreadySaved() {
        when(savedJobRepository.existsByUserIdAndJobId(1L, 100L)).thenReturn(true);
        savedJobService.saveJob(1L, 100L);
        verify(savedJobRepository, never()).save(any());
    }

    @Test
    void saveJob_UserNotFound() {
        when(savedJobRepository.existsByUserIdAndJobId(1L, 100L)).thenReturn(false);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> savedJobService.saveJob(1L, 100L));
    }

    @Test
    void unsaveJob_NotFound() {
        when(savedJobRepository.findByUserIdAndJobId(1L, 100L)).thenReturn(Optional.empty());
        savedJobService.unsaveJob(1L, 100L);
        verify(savedJobRepository, never()).delete(any());
    }
}
