package com.example.demo

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.xml.StaxEventItemReader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.oxm.jaxb.Jaxb2Marshaller


@Configuration
@EnableBatchProcessing
class Configuration {

    @Autowired
    lateinit var jobBuilderFactory: JobBuilderFactory

    @Autowired
    lateinit var stepBuilderFactory: StepBuilderFactory

    @Bean
    fun xmlFileItemReader(): ItemReader<Certificate> {

        val xmlFileReader = StaxEventItemReader<Certificate>()
        xmlFileReader.setResource(ClassPathResource("user1.xml"))
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
    fun certificateJob() = jobBuilderFactory
            .get("certificateJob")
            .start(certificateStep())
            .build()
}
