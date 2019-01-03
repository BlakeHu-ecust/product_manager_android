package com.product.productmanager.Model;

import java.util.ArrayList;

public class listModel<T> {

    private pageble pageble = new pageble();
    private ArrayList<T> content = new ArrayList<>();

    public pageble getPage() {
        return pageble;
    }

    public void setPage(pageble page) {
        this.pageble = page;
    }

    public ArrayList<T> getContent() {
        return content;
    }

    public void setContent(ArrayList<T> content) {
        this.content = content;
    }

    public class pageble{
        private int totalPage = 0;
        private int page = 0;
        private int totalRow = 0;
        private int pageSize = 0;

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getTotalRow() {
            return totalRow;
        }

        public void setTotalRow(int totalRow) {
            this.totalRow = totalRow;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }
    }
}
