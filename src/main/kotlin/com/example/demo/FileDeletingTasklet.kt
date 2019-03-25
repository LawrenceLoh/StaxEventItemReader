package com.example.demo

import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.UnexpectedJobExecutionException
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.core.io.Resource
import java.io.File


class FileDeletingTasklet : Tasklet {
    private var resources: Array<Resource>? = null

    fun setResources(resources: Array<Resource>) {
        this.resources = resources
    }

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        for (r in this.resources!!) {
            val file = r.file

            val deleted = file.renameTo(File(file.parent + File.separator + "done" + File.separator + file.name))
            if (!deleted) {
                throw UnexpectedJobExecutionException("Could not delete file " + file.path)
            }
        }
        return RepeatStatus.FINISHED
    }

}
