package org.community.itsuda.handler
//
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration
//import org.springframework.boot.autoconfigure.SpringBootApplication
//
//import org.springframework.boot.runApplication
//import org.springframework.context.annotation.Bean
////import org.springframework.context.ConfigurableApplicationContext
//import org.springframework.context.annotation.ComponentScan
//import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
//
///**
// * 모노리틱한 구조라... 우선 람다 하나만 사용.. MSA를 위해 람다를 계속
// * 늘려가면서 띄우기는 좀 비용적이나 인프라적으로 부족
// */
//
//@EnableAutoConfiguration
//@ComponentScan(basePackages = ["org.community.itsuda.*"])
//@EnableMongoRepositories(basePackages = ["org.community.itsuda.*"])
//@SpringBootApplication
//class KotlinAwsLambdaApplication {
//    @Bean
//    fun greet(): (String) -> String {
//        return { input -> "Hello from kotlin $input" }
//    }
//}
//
//fun main(args: Array<String>) {
//    runApplication<KotlinAwsLambdaApplication>(*args)
//}



import com.amazonaws.serverless.exceptions.ContainerInitializationException
import com.amazonaws.serverless.proxy.internal.testutils.Timer
import com.amazonaws.serverless.proxy.model.AwsProxyRequest
import com.amazonaws.serverless.proxy.model.AwsProxyResponse
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestStreamHandler
import org.community.itsuda.ItsudaApplication

import javax.servlet.DispatcherType
import javax.servlet.FilterRegistration

import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.EnumSet

open class StreamLambdaHandler: RequestStreamHandler {
    companion object {
        var handler: SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse>

        init {
            try {
                handler = SpringBootLambdaContainerHandler.getAwsProxyHandler(ItsudaApplication::class.java)
            } catch (e: ContainerInitializationException) {
                e.printStackTrace()
                throw RuntimeException("Could not initialize Spring Boot application", e)
            }
        }
    }

    override fun handleRequest(inputStream: InputStream, outputStream: OutputStream, context: Context) {
        handler.proxyStream(inputStream, outputStream, context)
    }
}