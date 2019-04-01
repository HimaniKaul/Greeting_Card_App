package com.example.himani_k.greeting_card.Interface;

import com.example.himani_k.greeting_card.Module.Cards.CardStoreData;
import com.example.himani_k.greeting_card.Module.Category.CategorySuccess;
import com.example.himani_k.greeting_card.Module.Frames.FrameSuccess;
import com.example.himani_k.greeting_card.Module.Quotes.QuotesSuccess;
import com.example.himani_k.greeting_card.Module.Stickers.StickerSuccess;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestInterface {

    @GET("category")
    Call<CategorySuccess> getCatData();

    @GET("cards")
    Call<CardStoreData> getCardData();

    @GET("quotes")
    Call<QuotesSuccess> getQuoteData();

    @GET("frames")
    Call<FrameSuccess> getFrameData();

    @GET("stickers")
    Call<StickerSuccess> getStickerData();
}
