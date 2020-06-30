
@Api(description="描述")
@RestController
@RequestMapping("/test")
public class EduTeacherController {

    @Autowired
    private TestService testService;

    // 接口描述
    @ApiOperation(value = "逻辑删除")
    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name = "id", value = "讲师ID", required = true)
                                     @PathVariable String id) {
        boolean flag = testService.removeById(id);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

}

