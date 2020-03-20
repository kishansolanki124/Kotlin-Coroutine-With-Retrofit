package com.example.kotlincoroutinewithretrofit.models.responsemodels

data class UserModel(
    var ad: Ad? = null,
    var `data`: ArrayList<Data>,
    var page: Int? = null,
    var per_page: Int? = null,
    var total: Int? = null,
    var total_pages: Int? = null
) {
    data class Ad(
        var company: String? = null,
        var text: String? = null,
        var url: String? = null
    )

    data class Data(
        var avatar: String? = null,
        var email: String? = null,
        var first_name: String? = null,
        var id: Int? = null,
        var last_name: String? = null
    )
}