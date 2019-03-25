package com.example.demo

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/certificate")
class StartController {

    @Autowired
    internal var jobLauncher: JobLauncher? = null

    @Autowired
    lateinit var job: Job

    @GetMapping("/start")
    fun start() {
        val jobParameters = JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters()
        jobLauncher!!.run(job, jobParameters)

    }
}
