package com.example.expensetracker.data.local.Entity

data class NotificationItem(
    val title:String
)

val notifyList = listOf(
    NotificationItem("\uD83D\uDC4B MyFinance ilovasiga xush kelibsiz!\n" +
            "Bu yerda siz oylik kirim va xarajatlaringizni qulay kuzatishingiz, byudjet rejalashtirishingiz va hisobotlarni ko‘rishingiz mumkin."),

    NotificationItem(
        "\uD83E\uDDED MyFinance sizga yordam beradi:\n" +
                "Kirim va chiqimlarni tezda qo‘shish\n" +
                "Kategoriyalar bo‘yicha tahlil qilish\n" +
                "Oylik byudjet rejalashtirish\n" +
                "Moliyaviy o‘sishni kuzatish\n" +
                "Bugunoq boshlang!"
    )
)
