package com.example.backgroundtaskexercisetwo


import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var jobScheduler: JobScheduler
    companion object{

        const val jobId = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler

        startjobbtn.setOnClickListener {

            startJob()
        }


        stopjobbtn.setOnClickListener {

            stopJob()
        }




    }


    private fun stopJob() {
        jobScheduler.cancel(jobId)
    }

    private fun startJob() {
        Toast.makeText(this, "Job will start in few seconds...", Toast.LENGTH_SHORT).show()

        val jobService = ComponentName(this, MyJobScheduler::class.java)
        //setting constraints in Job Info
        val jobInfo = JobInfo
            .Builder(jobId, jobService)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            .build()


        jobScheduler.schedule(jobInfo)
        if (jobScheduler.schedule(jobInfo) <= 0) {
            Toast.makeText(this, "There is problem while scheduling job", Toast.LENGTH_SHORT).show()
        }

    }




}