**Mybatis-Plus**

 Mybatis-Plus 是 mybatis 的增强

极大的减少了编写sql/xml 的工作

配置信息

~~~yaml
mybatis-plus:
  mapper-locations: classpath:/mapper/**/*.xml
  global-config:
    db-config:
      # 开启主键自增
      id-type: a
      # 设置逻辑删除  1 删除， 0 不删除
      logic-delete-value: 1
      logic-not-delete-value: 0
~~~

bean 配置

~~~java
@Data
@TableName("pms_category")
public class CategoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 分类id
	 */
    // @TableId 设置 主键
	@TableId
	private Long catId;
	/**
	 * 分类名称
	 */
	private String name;
	/**
	 * 父分类id
	 */
	private Long parentCid;
	/**
	 * 层级
	 */
	private Integer catLevel;
	/**
	 * 是否显示[0-不显示，1显示]
	 */
    // @TableLogic 逻辑删除字段   修改默认的规则，  1 不删除 ， 0 删除
	@TableLogic(value = "1", delval = "0")
	private Integer showStatus;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 图标地址
	 */
	private String icon;
	/**
	 * 计量单位
	 */
	private String productUnit;
	/**
	 * 商品数量
	 */
	private Integer productCount;

    // 该字段为空时，不封装到对象
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    // 设置该字段不在数据库匹配， 是自定义的属性
	@TableField(exist = false)
	private List<CategoryEntity> children;

}

~~~



一些使用的例子

查询

```java
    @Test
    void select() {
     	// 条件
        QueryWrapper<BrandEntity> wrapper = new QueryWrapper<BrandEntity>().eq("brand_id", 1L);
     
        List<BrandEntity> list = brandService.list(wrapper);

        for(BrandEntity item : list) {
            System.out.println(item.toString());
        }
     }
```



修改

```java
    @Test
    void update() {
        BrandEntity entity = new BrandEntity();
        entity.setDescript("test11111..........");
        entity.setName("test1111");
        
        Wrapper<BrandEntity> wrapper = new QueryWrapper<BrandEntity>().eq("brand_id", 1L);
        
        boolean save = brandService.update(entity, wrapper);
    }
```



新增

```java
    @Test
    void save() {
        BrandEntity entity = new BrandEntity();
        entity.setDescript("test..........");
        entity.setName("test");
        boolean save = brandService.save(entity);
    }
```



删除

```java
    @Test
    void remove () {
        QueryWrapper<BrandEntity> wrapper = new QueryWrapper<BrandEntity>().eq("brand_id", 1L);
        brandService.remove(wrapper);
    }
```

