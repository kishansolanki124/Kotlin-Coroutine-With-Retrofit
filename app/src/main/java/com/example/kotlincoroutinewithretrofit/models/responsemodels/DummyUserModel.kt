package com.example.kotlincoroutinewithretrofit.models.responsemodels

data class DummyUserModel(
    var code: Int? = null,
    var `data`: ArrayList<Data> = ArrayList(),
    var meta: Meta? = null
) {
    data class Data(
        var created_at: String? = null,
        var email: String? = null,
        var gender: String? = null,
        var id: Int? = null,
        var name: String? = null,
        var status: String? = null,
        var updated_at: String? = null
    )

    data class Meta(
        var pagination: Pagination? = null
    ) {
        data class Pagination(
            var limit: Int? = null,
            var page: Int? = null,
            var pages: Int? = null,
            var total: Int? = null
        )
    }
}