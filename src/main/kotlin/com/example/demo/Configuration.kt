package com.example.demo

import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.file.MultiResourceItemReader
import org.springframework.batch.item.xml.StaxEventItemReader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.oxm.jaxb.Jaxb2Marshaller


@Configuration
@EnableBatchProcessing
class Configuration {

    @Autowired
    lateinit var jobBuilderFactory: JobBuilderFactory

    @Autowired
    lateinit var stepBuilderFactory: StepBuilderFactory

    var resources: Array<Resource>? = null

    @Bean
    fun xmlFileItemReader(): MultiResourceItemReader<Certificate> {

        var patternResolver = PathMatchingResourcePatternResolver()
        resources = patternResolver.getResources("file:/C:/Users/XINSLOH/Desktop/certificate/*.xml")
//        var resources = patternResolver.getResources("file:/C633L5CG5502Z7J/Users/CHUFONG/Desktop/shared/certificate/*.xml")
        val reader = MultiResourceItemReader<Certificate>()
        reader.setResources(resources!!)
        reader.setDelegate(reader())

        return reader
    }

    @Bean
    fun reader(): StaxEventItemReader<Certificate> {
        val xmlFileReader = StaxEventItemReader<Certificate>()
        xmlFileReader.setFragmentRootElementName("HGZXX")

        val unmarshaller = Jaxb2Marshaller()
        unmarshaller.setClassesToBeBound(arrayOf(Certificate::class.java))
        xmlFileReader.setUnmarshaller(unmarshaller)
        return xmlFileReader
    }

    @Bean
    fun certificateItemWriter(): ItemWriter<Certificate> {
        return CertificateItemWriter()
    }

    @Bean
    fun certificateStep() = stepBuilderFactory
            .get("certificateStep")
            .chunk<Certificate, Certificate>(100)
            .reader(xmlFileItemReader())
            .writer(certificateItemWriter())
            .build()

    @Bean
    fun step2(): Step {
        val task = FileDeletingTasklet()
        task.setResources(resources!!)
        return stepBuilderFactory.get("step2")
                .tasklet(task)
                .build()
    }

    @Bean
    fun certificateJob() = jobBuilderFactory
            .get("certificateJob")
            .start(certificateStep())
            .next(step2())
            .build()
}
