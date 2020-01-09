package com.qiuxs.base.entity.pojo;

import com.qiuxs.base.entity.Entity;
import com.qiuxs.base.util.Strings;

import javax.persistence.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@MappedSuperclass
public abstract class NumberIdEntity<ID extends Number> implements Entity<ID> {

    private static final long serialVersionUID = -7530111699332363124L;

    /** 非业务主键 */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    protected ID id;

    public NumberIdEntity() {
        super();
    }

    public NumberIdEntity(ID id) {
        super();
        this.id = id;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public boolean isPersisted() {
        return validEntityKeyPredicate(id);
    }

    public boolean isTransient() {
        return !validEntityKeyPredicate(id);
    }

    private Boolean validEntityKeyPredicate(Object value) {
        if (null == value) return Boolean.FALSE;
        if (value instanceof Number) return 0 != ((Number) value).intValue();
        return (null != value) && (value instanceof String) && Strings.isNotEmpty((String) value);
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return (null == id) ? 629 : this.id.hashCode();
    }

    /**
     * <p>
     * 比较id,如果任一方id是null,则不相等
     * </p>
     * 由于业务对象被CGlib或者javassist增强的原因，这里只提供一般的基于id的比较,不提供基于Class的比较。<br>
     * 如果在存在继承结构， 请重置equals方法。
     *
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(final Object object) {
        if (this == object) return true;
        if (!(object instanceof NumberIdEntity)) { return false; }
        NumberIdEntity<?> rhs = (NumberIdEntity<?>) object;
        if (null == getId() || null == rhs.getId()) { return false; }
        return getId().equals(rhs.getId());
    }


    //得到一个验证器实例
    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    //验证方法,抛出自定义异常
    public void validate() throws RuntimeException{
        //用Map保存错误
        Map<String, StringBuffer> errorMap = null;
        //把对象放到验证器的验证方法中，用Set存储违背约束的对象
        Set<ConstraintViolation<NumberIdEntity<ID>>> set = validator.validate(this, Default.class);
        //当有违背约束的对象时
        if (set != null && set.size() > 0) {
            //初始化map
            errorMap = new HashMap<String, StringBuffer>();
            //保存错误属性
            String property = null;
            for (ConstraintViolation<NumberIdEntity<ID>> cv : set) {
                // 这里循环获取错误信息，可以自定义格式
                property = cv.getPropertyPath().toString();
                if (errorMap.get(property) != null) {
                    errorMap.get(property).append("," + cv.getMessage());
                } else {
                    StringBuffer sb = new StringBuffer();
                    sb.append(cv.getMessage());
                    errorMap.put(property, sb);
                }
            }

            if (errorMap != null) {
                StringBuffer sb = new StringBuffer();
                for (Map.Entry<String, StringBuffer> m : errorMap.entrySet()) {
                    sb.append(m.getValue().toString()).append("\r\n");
                }
                throw new RuntimeException(sb.toString());
            }

        }

    }


}
