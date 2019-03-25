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
import org.springframework.oxm.xstream.XStreamMarshaller
import java.util.HashMap




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
        xmlFileReader.setResource(ClassPathResource("user.xml"))
        xmlFileReader.setFragmentRootElementName("person")

        val aliasesMap = HashMap<String, String>()
        aliasesMap["person"] = "com.example.demo.Certificate"
        val marshaller = XStreamMarshaller()
        marshaller.setAliases(aliasesMap)

        xmlFileReader.setUnmarshaller(marshaller)

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