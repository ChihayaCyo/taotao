package pojo;

import com.taotao.pojo.TbItem;

/**
 * Created by zh on 3/9/2017.
 */
public class ItemInfo extends TbItem {

    public String[] getImages() {
        String image = getImage();
        if (image != null) {
            String[] images = image.split(",");
            return images;
        }
        return null;
    }
}

