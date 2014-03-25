package com.example.imagetest;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.image.ImageCache;
import com.example.image.ImageCache.ImageCacheParams;
import com.example.image.ImageFetcher;

public class MainActivity extends Activity {
	
	private final String PATH = Environment.getExternalStorageDirectory().getPath() + "/LiaoTest"; // 保存文件的路径
	private final String PATH_IMAGE = "/ImageCache/";
	
	String[] image_url = {
			"http://ww4.sinaimg.cn/bmiddle/cf28783ctw1eap13hltzej20bd0gu75s.jpg",
			"http://ww1.sinaimg.cn/bmiddle/cf28783ctw1eap13ne54pj20az050aai.jpg",
			"http://ww2.sinaimg.cn/bmiddle/cf28783ctw1eap13t5g2kj20bb0gq75o.jpg",
			"http://ww1.sinaimg.cn/bmiddle/cf28783ctw1eap13uvefej20bd0gqtaa.jpg",
			"http://ww2.sinaimg.cn/bmiddle/cf28783ctw1eap13zvw70j20at050gm5.jpg",
			"http://ww3.sinaimg.cn/bmiddle/cf28783ctw1eap1486fv6j20bc0gujsp.jpg",
			"http://ww3.sinaimg.cn/bmiddle/cf28783ctw1eap14d2mh0j208l07ot9a.jpg",
			"http://ww1.sinaimg.cn/bmiddle/cf28783ctw1eap14ekniqj208f077jrs.jpg",
			"http://ww2.sinaimg.cn/bmiddle/cf28783ctw1eap14gg0bhj20cy0bfq48.jpg",
			"http://ww1.sinaimg.cn/bmiddle/63918611jw1eaq2pnksc9j209q07b0t3.jpg",
			"http://ww3.sinaimg.cn/bmiddle/a0c74f04jw1eaph04rhajj20c807rq4a.jpg",
			"http://ww1.sinaimg.cn/bmiddle/ac80f176jw1e88k9x9s3kj20c80g8dgn.jpg",
			"http://ww1.sinaimg.cn/bmiddle/ac80f176jw1e88k9y5yx8j20c808274m.jpg"};
	
	
	ListView lv_image;
	Context context;
	ImageFetcher imageFetcher;
	MySimpleAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		lv_image = (ListView)findViewById(R.id.lv);
		context = this;
		//设置图片大小
		imageFetcher = new ImageFetcher(this, 120, 120);
//		imageFetcher = new ImageFetcher(this, 0);
		//设置缓存路径
		imageFetcher.setImageCache(getImageCache());
		//设置过渡图片
		imageFetcher.setLoadingImage(R.drawable.ic_launcher);
		//设置图片淡入显示
		imageFetcher.setImageFadeIn(true);
		
		adapter = new MySimpleAdapter();
		lv_image.setAdapter(adapter);
		
	}
	
	class MySimpleAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return image_url.length;
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHandler handler;
			if(convertView == null){
				handler = new ViewHandler();
				convertView = LayoutInflater.from(context).inflate(R.layout.item_image_list, null);
				handler.iv_image = (ImageView)convertView.findViewById(R.id.iv_image);
				handler.tv_url = (TextView)convertView.findViewById(R.id.tv_url);
				convertView.setTag(handler);
			}else{
				handler = (ViewHandler) convertView.getTag();
			}
			
			imageFetcher.loadImage(image_url[position], handler.iv_image);
			handler.tv_url.setText(image_url[position]);
			
			return convertView;
		}
		
		class ViewHandler{
			ImageView iv_image;
			TextView tv_url;
		}
		
	}
	
	public ImageCache getImageCache() {
		ImageCache mImageCache = null;
		if (mImageCache == null) {
			ImageCacheParams imageCacheParams = new ImageCacheParams(
					new File(getImagePath(this)));
			imageCacheParams.setMemCacheSizePercent(this, 0.25f);
			imageCacheParams.compressFormat = CompressFormat.PNG;
			mImageCache = new ImageCache(imageCacheParams);
		};
		return mImageCache;
	}
	
	public String getImagePath(Context context) {
		File dir = null;
		if (isExistSDcard()) {
    		dir = new File(PATH + PATH_IMAGE);
    	} else {
    		dir = new File(getDataPath(context) + PATH_IMAGE);
    	}
		return dir.getPath();
	}
	
	/**
     * 检测SDcard是否存在
     * @return
     */
	public static boolean isExistSDcard() {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED))
			return true;
		else {
			return false;
		}
	}
	
	/**
	 * 获取内部存储路径
	 * @param context
	 * @return
	 */
	public static String getDataPath(Context context) {
		return "/data/data/" + context.getPackageName();
	}


}
