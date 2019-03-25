package com.example.demo

import org.springframework.batch.item.ItemWriter
import org.springframework.beans.factory.annotation.Autowired

class CertificateItemWriter : ItemWriter<Certificate> {

    @Autowired
    lateinit var a:CertificateRepository

    override fun write(items: List<Certificate>) {
        for(i in items){
            println(i.toString())
        }
        a.saveAll(items)
    }

}
