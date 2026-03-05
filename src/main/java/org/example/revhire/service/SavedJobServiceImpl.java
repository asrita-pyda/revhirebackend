package org.example.revhire.service;

import org.example.revhire.dto.response.JobResponse;
import org.example.revhire.model.Job;
import org.example.revhire.model.SavedJob;
import org.example.revhire.model.User;
import org.example.revhire.repository.JobRepository;
import org.example.revhire.repository.SavedJobRepository;
import org.example.revhire.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SavedJobServiceImpl implements SavedJobService {

    private final SavedJobRepository savedJobRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;

    public SavedJobServiceImpl(SavedJobRepository savedJobRepository, UserRepository userRepository,
                               JobRepository jobRepository) {
        this.savedJobRepository = savedJobRepository;
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
    }
    // We need mapToDto logic here, duplication, or better inject JobService but
    // JobService might use SavedJobService later.
    // Ideally mapToDto should be in a Mapper component. For now I'll duplicate
    // minimal mapping or just return Job entities wrapped in DTO.
    // Or I'll just use a Mapper method here.

    @Override
    @Transactional
    public void saveJob(Long userId, Long jobId) {
        if (savedJobRepository.existsByUserIdAndJobId(userId, jobId)) {
            return; // Already saved
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        SavedJob savedJob = new SavedJob();
        savedJob.setUser(user);
        savedJob.setJob(job);
        savedJobRepository.save(savedJob);
    }

    @Override
    @Transactional
    public void unsaveJob(Long userId, Long jobId) {
        savedJobRepository.findByUserIdAndJobId(userId, jobId)
                .ifPresent(savedJobRepository::delete);
    }

    @Override
    public List<JobResponse> getSavedJobs(Long userId) {
        List<SavedJob> savedJobs = savedJobRepository.findByUserId(userId);
        return savedJobs.stream()
                .map(sj -> mapToDto(sj.getJob()))
                .collect(Collectors.toList());
    }

    // Quick mapper for now
    private JobResponse mapToDto(Job job) {
        JobResponse dto = new JobResponse();
        dto.setId(job.getId());
        dto.setEmployerId(job.getEmployer().getId());
        dto.setEmployerName(job.getEmployer().getName());
        dto.setTitle(job.getTitle());
        dto.setDescription(job.getDescription());
        dto.setRequirements(job.getRequirements());
        dto.setLocation(job.getLocation());
        dto.setSalaryMin(job.getSalaryMin());
        dto.setSalaryMax(job.getSalaryMax());
        dto.setJobType(job.getJobType());
        dto.setExperienceYears(job.getExperienceYears());
        dto.setOpenings(job.getOpenings());
        dto.setDeadline(job.getDeadline());
        dto.setStatus(job.getStatus());
        dto.setPostedAt(job.getCreatedAt());
        // skipping skills for brevity on saved list
        return dto;
    }
}
