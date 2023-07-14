package io.github.cdsap.compare.view

import com.jakewharton.picnic.TextAlignment
import com.jakewharton.picnic.table
import io.github.cdsap.compare.model.Measurement
import java.io.File

class ExperimentView {

    private val LIMIT_DIFFERENCE_LONG = 1000L
    private val LIMIT_DIFFERENCE_INT = 1000

    fun print(measurement: List<Measurement>) {
        println(generateTable(measurement))
        File("results_experiment").writeText(generateHtmlTable(measurement))
    }

    private fun generateTable(measurement: List<Measurement>) =
        table {
            cellStyle {
                border = true
                alignment = TextAlignment.MiddleLeft
                paddingLeft = 1
                paddingRight = 1
            }
            body {
                row {
                    cell("Experiment") {
                        columnSpan = 4
                        alignment = TextAlignment.MiddleCenter
                    }
                }

                measurement.groupBy {
                    it.OS
                }.forEach {
                    row {
                        cell(it.key.name) {
                            columnSpan = 4
                            alignment = TextAlignment.MiddleCenter
                        }
                    }
                    row {
                        cell("Metric")
                        cell("VARIANT A")
                        cell("VARIANT B")
                        cell("Improvement")
                    }
                    it.value.forEach {
                        if (it.variantA is Long) {

                            row {
                                cell(it.name)
                                cell(it.variantA)
                                cell(it.variantB)
                                cell(it.diff()) {
                                    alignment = TextAlignment.MiddleRight
                                }
                            }

                        }
                        if (it.variantA is Int) {
                            row {
                                cell(it.name)
                                cell(it.variantA)
                                cell(it.variantB)
                                cell(it.diff()) {
                                    alignment = TextAlignment.MiddleRight
                                }

                            }
                        }
                        if (it.variantA is String) {
                            row {
                                cell(it.name)
                                cell(it.variantA)
                                cell(it.variantB)
                                cell("") {
                                }
                            }
                        }
                    }
                }
            }
        }

    private fun generateHtmlTable(measurement: List<Measurement>): String {
        var output = ""
        output += "<table><tr><td colspan=4>Experiment</td></tr>"
        output += "<tr><td>Metric</td><td>VARIANT A</td><td>VARIANT B</td><td>Improvement</td></tr>"

        measurement.groupBy {
            it.OS
        }.forEach {
            it.value.forEach {
                if (it.variantA is Long) {
                    output += "<tr><td>${it.name}</td><td>${it.variantA}</td><td>${it.variantB}</td><td>${it.diff()}</td></tr>"

                }
                if (it.variantA is Int) {
                    output += "<tr><td>${it.name}</td><td>${it.variantA}</td><td>${it.variantB}</td><td>${it.diff()}</td></tr>"
                }
                if (it.variantA is String) {
                    output += "<tr><td>${it.name}</td><td>${it.variantA}</td><td>${it.variantB}</td><td></td></tr>"
                }

            }
        }
        output += "</table>"
        return output
    }
}