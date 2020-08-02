package com.example.gotravel.presentation.home

enum class SortBy {

    Featured,
    PriceLowToHigh,
    PriceHighToLow,
    TimeNewest,
    TimeOldest;

    override fun toString(): String {
        return name.capitalize()
    }
}