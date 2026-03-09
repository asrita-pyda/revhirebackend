const http = require('http');
const fs = require('fs');

const uniqueId = Date.now();
const email = `emp${uniqueId}@test.com`;

let logOutput = "";

const regPayload = JSON.stringify({
    name: "Test Employer",
    email: email,
    password: "password123",
    role: "EMPLOYER",
    phone: "555-0101",
    companyName: "TestCorp",
    industry: "Tech",
    headquartersLocation: "NY"
});

const regOptions = {
    hostname: 'localhost',
    port: 8080,
    path: '/api/auth/register',
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
};

const req = http.request(regOptions, (res) => {
    let regData = '';
    res.on('data', d => regData += d);
    res.on('end', () => {
        logOutput += "--- REGISTRATION RESPONSE ---\\n" + regData + "\\n\\n";
        loginAndPostJob();
    });
});
req.write(regPayload);
req.end();

function loginAndPostJob() {
    const authPayload = JSON.stringify({ email: email, password: 'password123' });
    const authOptions = {
        hostname: 'localhost',
        port: 8080,
        path: '/api/auth/login',
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
    };

    const req = http.request(authOptions, (res) => {
        let authData = '';
        res.on('data', d => authData += d);
        res.on('end', () => {
            logOutput += "--- LOGIN RESPONSE ---\\n" + authData + "\\n\\n";
            let parsedAuth;
            try {
                parsedAuth = JSON.parse(authData);
            } catch (e) { }

            if (!parsedAuth || !parsedAuth.success) {
                fs.writeFileSync('test-out.log', logOutput);
                return;
            }

            const token = parsedAuth.data.token;
            const employerId = parsedAuth.data.id;

            const jobPayload = JSON.stringify({
                employerId: employerId,
                title: "Senior Frontend Developer",
                description: "Looking for an expert Angular developer.",
                location: "Remote",
                salaryMin: 80000,
                salaryMax: 120000,
                jobType: "FULLTIME",
                experienceYears: 5,
                openings: 2,
                skills: ["Angular", "TypeScript"],
                deadline: new Date(new Date().setFullYear(new Date().getFullYear() + 1)).toISOString().split('T')[0]
            });

            const jobOptions = {
                hostname: 'localhost',
                port: 8080,
                path: '/api/jobs',
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
            };

            const jobReq = http.request(jobOptions, (jobRes) => {
                let jobResponseData = '';
                jobRes.on('data', d => jobResponseData += d);
                jobRes.on('end', () => {
                    logOutput += "--- JOB POST RESPONSE (" + jobRes.statusCode + ") ---\\n" + jobResponseData;
                    fs.writeFileSync('test-out.log', logOutput);
                });
            });
            jobReq.write(jobPayload);
            jobReq.end();
        });
    });
    req.write(authPayload);
    req.end();
}
