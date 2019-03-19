//package com.github.chizoba.starwarz.data.movies
//
//import com.github.chizoba.starwarz.data.Result
//import com.github.chizoba.starwarz.domain.models.MovieResponse
//import com.github.chizoba.starwarz.domain.models.MoviesResponse
//import retrofit2.Response
//
//class TestMoviesDataSource : MoviesDataSource {
//    override fun loadMovies(): Result<MoviesResponse> {
//        Thread.sleep(3000)
//        return Result.Success(
//            listOf(
//                MoviesResponse(1),
//                MoviesResponse(2),
//                MovieResponse(3),
//                MovieResponse(4)
//            )
//        )
////        return try {
////            val response = service.getMovies().execute()
////            resolveResponse(response = response, onError = {
////                Result.Error(Exception("Error getting movies ${response.code()} ${response.message()}"))
////            })
////        } catch (e: IOException) {
////            Result.Error(IOException("Error getting movies", e))
////        }
//
//    }
//
//    private fun resolveResponse(
//        response: Response<List<MovieResponse>>,
//        onError: () -> Result.Error
//    ): Result<List<MovieResponse>> {
//        if (response.isSuccessful) {
//            val body = response.body()
//            if (body != null) {
//                return Result.Success(body)
//            }
//        }
//        return onError.invoke()
//    }
//
//}
