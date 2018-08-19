import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

interface Api {

    companion object {
        operator fun invoke(retrofit: Retrofit = instance): Api {
            return retrofit.create(Api::class.java)
        }

        private val instance: Retrofit by lazy {
            Retrofit.Builder()
                    .baseUrl("https://www.reddit.com/r/soccerstreams/")
                    .addConverterFactory(JacksonConverterFactory.create())
                    .client(OkHttpClient.Builder()
                            .build())
                    .build()
        }
    }

    @GET("top/.json")
    fun getTop(): Call<RootResponse>

    @GET
    fun getDetails(@Url url: String): Call<List<PostDetail>>
}