package com.lingxi.preciousmetal.util.net;

public interface HttpResultListener {
      void onSuccess(String data);
	  void onFailure(String data);
}