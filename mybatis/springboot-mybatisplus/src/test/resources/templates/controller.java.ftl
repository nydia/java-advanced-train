package ${package.Controller};

import org.springframework.web.bind.annotation.RequestMapping;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName?? && package.ModuleName != "">/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

   private Logger logger = LoggerFactory.getLogger(getClass());

   @Autowired
   private ${table.serviceName} i${entity}Service;

   /**
   * 查询分页数据
   */
   @ApiOperation(value = "查询分页数据")
   @GetMapping(value = "/list")
   public R<PageResultDto<${entity}>> findListByPage(@ApiParam ${entity} entity,
       @RequestParam(defaultValue = "1") int pageNum,
       @RequestParam(defaultValue = "50") int pageSize) {
      Page<${entity}> list = i${entity}Service.page(new Page<>(pageNum, pageSize));
      return R.success(CommonPage.restPage(list));
   }


   /**
   * 根据id查询
   */
   @ApiOperation(value = "根据id查询数据")
   @GetMapping(value = "/{id}")
   public R<${entity}> getById(@PathVariable Long id) {
      ${entity} entity = i${entity}Service.getById(id);
      return R.success(entity);
   }

   /**
   * 新增
   */
   @ApiOperation(value = "新增数据")
   @PostMapping(value = "/create")
   public R<Boolean> add(@ApiParam @RequestBody ${entity} entity) {
      boolean result = i${entity}Service.saveOrUpdate(entity);
      return R.success(result);
   }

   /**
   * 修改
   */
   @ApiOperation(value = "修改数据")
   @PostMapping(value = "/update")
   public R<Boolean> add(@ApiParam @RequestBody ${entity} entity) {
      boolean result = i${entity}Service.saveOrUpdate(entity);
      return R.success(result);
   }

   /**
   * 删除单条记录
   */
   @ApiOperation(value = "删除单条记录")
   @PostMapping(value = "/delete/{id}")
   public R<Boolean> delete(@PathVariable Long id) {
      boolean result = i${entity}Service.removeById(id);
      return R.success(result);
   }

}
</#if>
