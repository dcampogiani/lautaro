import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class RootResponse(@JsonProperty("data") val data: Data?) {


    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Data(@JsonProperty("children") val children: List<Child>?) {


        @JsonIgnoreProperties(ignoreUnknown = true)
        data class Child(@JsonProperty("data") val data: Data?) {

            @JsonIgnoreProperties(ignoreUnknown = true)
            class Data(@JsonProperty("title") val title: String?,
                       @JsonProperty("url") val url: String?)

        }
    }
}

