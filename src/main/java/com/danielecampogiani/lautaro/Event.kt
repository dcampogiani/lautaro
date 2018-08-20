package com.danielecampogiani.lautaro

data class Event(
    val timeGMT: String,
    val homeTeam: String,
    val awayTeam: String,
    val url: String) {
}