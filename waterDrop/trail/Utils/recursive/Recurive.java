public class Recurive{

//========================递归查询所有菜单================================================
    //获取全部菜单
    @Override
    public List<Permission> queryAllMenuGuli() {
        //1 查询菜单表所有数据
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        List<Permission> permissionList = baseMapper.selectList(wrapper);
        //2 把查询所有菜单list集合按照要求进行封装
        List<Permission> resultList = bulidPermission(permissionList);
        return resultList;
    }

    //把返回所有菜单list集合进行封装的方法
    public static List<Permission> bulidPermission(List<Permission> permissionList) {

        //创建list集合，用于数据最终封装
        List<Permission> finalNode = new ArrayList<>();
        //把所有菜单list集合遍历，得到顶层菜单 pid=0菜单，设置level是1
        for(Permission permissionNode : permissionList) {
            //得到顶层菜单 pid=0菜单
            if("0".equals(permissionNode.getPid())) {
                //设置顶层菜单的level是1
                permissionNode.setLevel(1);
                //根据顶层菜单，向里面进行查询子菜单，封装到finalNode里面
                finalNode.add(selectChildren(permissionNode,permissionList));
            }
        }
        return finalNode;
    }

    private static Permission selectChildren(Permission permissionNode, List<Permission> permissionList) {
        //1 因为向一层菜单里面放二层菜单，二层里面还要放三层，把对象初始化
        permissionNode.setChildren(new ArrayList<Permission>());

        //2 遍历所有菜单list集合，进行判断比较，比较id和pid值是否相同
        for(Permission it : permissionList) {
            //判断 id和pid值是否相同
            if(permissionNode.getId().equals(it.getPid())) {
                //把父菜单的level值+1
                int level = permissionNode.getLevel()+1;
                it.setLevel(level);
                //如果children为空，进行初始化操作
                if(permissionNode.getChildren() == null) {
                    permissionNode.setChildren(new ArrayList<Permission>());
                }
                //把查询出来的子菜单放到父菜单里面
                permissionNode.getChildren().add(selectChildren(it,permissionList));
            }
        }
        return permissionNode;
    }

    //============递归删除菜单==================================
    @Override
    public void removeChildByIdGuli(String id) {
        //1 创建list集合，用于封装所有删除菜单id值
        List<String> idList = new ArrayList<>();
        //2 向idList集合设置删除菜单id
        this.selectPermissionChildById(id,idList);
        //把当前id封装到list里面
        idList.add(id);
        baseMapper.deleteBatchIds(idList);
    }

    //2 根据当前菜单id，查询菜单里面子菜单id，封装到list集合
    private void selectPermissionChildById(String id, List<String> idList) {
        //查询菜单里面子菜单id
        QueryWrapper<Permission>  wrapper = new QueryWrapper<>();
        wrapper.eq("pid",id);
        wrapper.select("id");
        List<Permission> childIdList = baseMapper.selectList(wrapper);
        //把childIdList里面菜单id值获取出来，封装idList里面，做递归查询
        childIdList.stream().forEach(item -> {
            //封装idList里面
            idList.add(item.getId());
            //递归查询
            this.selectPermissionChildById(item.getId(),idList);
        });
    }

}