package org.gis.pdf.data;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.gis.pdf.json.JSONArray;
import org.gis.pdf.json.JSONObject;
import org.gis.pdf.util.ImageUtil;

public class OverlayLayer implements Overlayable {
  
    protected URL url;
    protected BufferedImage image;
    protected float transparency;
    
    public OverlayLayer(URL imageUrl, float transparency) throws Exception {
      this.url = imageUrl;
      this.transparency = transparency;
      //a little housekeeping
      image = null;
      try{
        ImageUtil util = new ImageUtil();
        image = util.readImage(url);
      }catch(Exception e){
        throw e;
      }
    }
    
    public URL getImageUrl(){
      return url;
    }
    
    public float getTransparency(){
      return transparency;
    }

    public BufferedImage getImage(){
      return image;
    }

    public int getHeight() {
      return image != null ? image.getHeight() : 0;
    }

    public int getWidth() {
      return image != null ? image.getWidth() : 0;
    }
    
    public static OverlayLayer fromJson(JSONObject json) throws Exception{
      OverlayLayer layer = null;
      try{
        String url = json.getString("url");
        float transparency = (float) json.getDouble("transparency");
        layer = new OverlayLayer(new URL(url), transparency);
      }catch (Exception e){
        throw new Exception("Invalid input json, " + e.getMessage(), e);
      }
      return layer;
    }
    
    public static List<OverlayLayer> fromJson(JSONArray json) throws Exception {
      List<OverlayLayer> layers = new ArrayList<OverlayLayer>();
        try{
    	  for(int i=0; i<json.length(); i++){
    	    OverlayLayer layer = fromJson(json.getJSONObject(i));
    		layers.add(layer);
    	  }	
    	}catch (Exception e){
        throw new Exception("Invalid input json, " + e.getMessage(), e);
    	}
      return layers;
    }
    
    public int getType() {
      return image.getType();
    }
}
