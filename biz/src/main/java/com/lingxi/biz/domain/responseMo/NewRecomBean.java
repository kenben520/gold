package com.lingxi.biz.domain.responseMo;

import java.io.Serializable;
import java.util.List;

public class NewRecomBean extends BaseMo {

    /**
     * menus : [{"name":"推荐","fixed":1,"value":1},{"name":"快讯","fixed":1,"value":2},{"name":"国际要闻","fixed":0,"value":"20"},{"name":"每日头条","fixed":0,"value":"26"},{"name":"行情走势","fixed":0,"value":"27"},{"name":"机构观点","fixed":0,"value":"28"}]
     * items : [{"title":"伊朗和伊拉克通过互换交易加强联系，或加剧中东的紧张情绪","time":"2018-06-05 11:35:37","link":"http://msd.cn/index.php?app=art&act=view&aid=986","thumbnail":"http://www.msd.cn/tes/ties/dault/mg/logo.svg"},{"title":"美元或经历动荡的一周","time":"2018-06-05 11:30:17","link":"http://msd.cn/index.php?app=acle&act=_app&id=990","thumbnail":"http://www.msd.cn/themes/s/deult/img/logo.svg"}]
     * bannels : [{"title":"广告1","image":"https://feyuncs.com/uplads/impc/200621/9231.jpg","link":"http://www.baidu.com"},{"title":"广告1","image":"https://aliycs.com/upoads/im0621/152919440.jpg","link":"http://www.baidu.com"}]
     * page : 1
     * total : 12
     * countpage : 2
     */

    private int page;
    private int total;
    private int countpage;
    private List<ItemsBean> items;
    private List<BannelsBean> bannels;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCountpage() {
        return countpage;
    }

    public void setCountpage(int countpage) {
        this.countpage = countpage;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public List<BannelsBean> getBannels() {
        return bannels;
    }

    public void setBannels(List<BannelsBean> bannels) {
        this.bannels = bannels;
    }

    public static class ItemsBean  implements Serializable {
        /**
         * title : 伊朗和伊拉克通过互换交易加强联系，或加剧中东的紧张情绪
         * time : 2018-06-05 11:35:37
         * link : http://msd.cn/index.php?app=art&act=view&aid=986
         * thumbnail : http://www.msd.cn/tes/ties/dault/mg/logo.svg
         */

        private String title;
        private String time;
        private String link;
        private String thumbnail;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }
    }

    public static class BannelsBean {
        /**
         * title : 广告1
         * image : https://feyuncs.com/uplads/impc/200621/9231.jpg
         * link : http://www.baidu.com
         */

        private String title;
        private String image;
        private String link;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }
}
