package com.danielecampogiani.lautaro.internal

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
internal data class PostDetail(@JsonProperty("data") val data: Data?) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    internal data class Data(@JsonProperty("children") val children: List<Child>?) {

        @JsonIgnoreProperties(ignoreUnknown = true)
        internal data class Child(@JsonProperty("data") val data: Data?) {

            @JsonIgnoreProperties(ignoreUnknown = true)
            internal class Data(@JsonProperty("body") val body: String?)
        }
    }
}