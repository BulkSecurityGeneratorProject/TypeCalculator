package com.mycompany.myapp.domain;

import com.mycompany.myapp.validation.ValidRule;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PackageType.
 */
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
@Table(name = "package_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PackageType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 1)
    @Column(name = "type", nullable = false)
    private String type;

    @NotNull
    @Size(min = 1)
    @Column(name = "name", nullable = false)
    private String name;

    @Size(min = 1)
    @Column(name = "description")
    private String description;

    @NotNull
    @Size(min = 1)
    @Column(name = "rule", nullable = false)
    @ValidRule
    private String rule;

    public boolean matches(Box box) {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        engine.put("box",box);
        try {
            return (boolean) engine.eval(rule);
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
    }
}
