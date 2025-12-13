package eu.tutorials.koperasi_simpan_pinjam.data.repository.user

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import eu.tutorials.koperasi_simpan_pinjam.data.API.ApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class UserNasabahRepository(private val api : ApiService) {
    suspend fun uploadProfileImage(
        userId: String,
        bytes: ByteArray,
        fileName: String
    ): Result<String> {
        return try {
            val requestBody =
                bytes.toRequestBody("image/*".toMediaTypeOrNull())

            val multipart =
                MultipartBody.Part.createFormData(
                    "image",
                    fileName,
                    requestBody
                )

            val response = api.uploadProfileImage(userId, multipart)

            if (response.isSuccessful) {
                Result.success(response.body()?.message ?: "Uploaded")
            } else {
                Result.failure(Exception("Upload failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getProfileImage(userId: String): Result<Bitmap> {
        return try {
            val response = api.getProfileImage(userId)

            if (response.isSuccessful) {
                val bytes = response.body()!!.bytes()
                val bitmap = BitmapFactory.decodeByteArray(
                    bytes, 0, bytes.size
                )
                Result.success(bitmap)
            } else {
                Result.failure(Exception("Image not found"))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}