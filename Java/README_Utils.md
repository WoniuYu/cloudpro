**java 常用方法**

###### 生成树

~~~java
    @Override
    public List<CategoryEntity> listWithTree() {
        // 获取数据
        List<CategoryEntity> categoryEntities = baseMapper.selectList(null);

        // 获取树形结构
        List<CategoryEntity> collect = categoryEntities.stream().filter(categoryEntitie -> {
            // 跟节点标志
            return categoryEntitie.getParentCid() == 0;
        }).map(item -> {
             item.setChildren(getChildrenList(item, categoryEntities));
             return item;
        }).sorted((menu1, menu2) ->{
            // 排序方式  由小到大  字段 sort
            return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort() );
        }).collect(Collectors.toList());
        return collect;
    }

	// 递归遍历子节点
    private List<CategoryEntity> getChildrenList(CategoryEntity parent, List<CategoryEntity> categoryEntities) {
        List<CategoryEntity> collect = categoryEntities.stream().filter(categoryEntitie -> {
            // 字节点与父节点的关联
            return categoryEntitie.getParentCid() == parent.getCatId();
        }).map(item -> {
            item.setChildren(getChildrenList(item, categoryEntities));
            return item;
        }).sorted((menu1, menu2) ->{
            return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort() );
        }).collect(Collectors.toList());
        return collect;
    }
~~~

