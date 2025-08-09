package com.shiftx.util

import android.content.Context
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import java.io.File

object PdfExporter {
    fun exportMonthlySchedule(context: Context, fileName: String = "monthly_schedule.pdf"): File {
        val file = File(context.getExternalFilesDir(null), fileName)
        PdfWriter(file).use { writer ->
            val pdf = PdfDocument(writer)
            val doc = Document(pdf)
            doc.add(Paragraph("Monthly Schedule (stub)"))
            doc.close()
        }
        return file
    }

    fun exportLeaveSummary(context: Context, fileName: String = "leave_summary.pdf"): File {
        val file = File(context.getExternalFilesDir(null), fileName)
        PdfWriter(file).use { writer ->
            val pdf = PdfDocument(writer)
            val doc = Document(pdf)
            doc.add(Paragraph("Leave Summary (stub)"))
            doc.close()
        }
        return file
    }

    fun exportFplReport(context: Context, fileName: String = "fpl_report.pdf"): File {
        val file = File(context.getExternalFilesDir(null), fileName)
        PdfWriter(file).use { writer ->
            val pdf = PdfDocument(writer)
            val doc = Document(pdf)
            doc.add(Paragraph("FPL Report (stub)"))
            doc.close()
        }
        return file
    }
}