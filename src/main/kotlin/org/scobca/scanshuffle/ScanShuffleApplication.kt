package org.scobca.scanshuffle

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ScanShuffleApplication

fun main(args: Array<String>) {
    runApplication<ScanShuffleApplication>(*args)
}
