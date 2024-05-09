package com.winteryy.nbcsearch.presentation.search

/**
 * 페이징 처리를 위해 만든 클래스입니다.
 *
 * @property keyword 검색했던 검색어
 * @property imagePage 가장 마지막에 노출했던 이미지 API의 페이지
 * @property videoPage 가장 마지막에 노출했던 비디오 API의 페이지
 * @property imageIsEnd 이미지 API로부터 페이지 끝임을 전달 받았는 지 확인
 * @property videoIsEnd 비디오 API로부터 페이지 끝임을 전달 받았는 지 확인
 */
data class PagingMeta(
    val keyword: String,
    val imagePage: Int,
    val videoPage: Int,
    val imageIsEnd: Boolean,
    val videoIsEnd: Boolean,
)
