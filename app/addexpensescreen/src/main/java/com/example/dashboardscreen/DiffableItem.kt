package com.example.dashboardscreen

interface DiffableItem {
    fun areItemsTheSame(other: DiffableItem): Boolean
    fun areContentsTheSame(other: DiffableItem): Boolean
}