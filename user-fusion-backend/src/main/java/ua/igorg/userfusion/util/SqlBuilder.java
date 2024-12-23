package ua.igorg.userfusion.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.igorg.userfusion.config.datasources.model.DataSource;
import ua.igorg.userfusion.config.datasources.model.Mapping;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Created by igorg on 19.12.2024
 */
@Slf4j
@RequiredArgsConstructor
public class SqlBuilder {

    private final String baseQuery;
    private final DataSource dataSource;
    private final Map<FieldName, Optional<String>> inputParams;
    private final List<String> parameters;

    private Mapping mapping = new Mapping();

    interface FourConsumer<T, U, V, R> {
        void accept(T t, U u, V v, R r);
    }

    public String build() {
        mapping = dataSource.getMapping();
        final StringBuilder sql = new StringBuilder(baseQuery.formatted(
                mapping.getId(),
                mapping.getUsername(),
                mapping.getName(),
                mapping.getSurname(),
                dataSource.getTable()));

        log.debug("Base query: {}", sql);

        inputParams.entrySet().stream()
                .filter(e -> e.getValue().isPresent())
                .forEach(e -> extractor.accept(e.getValue(), e.getKey(), parameters, sql));

        log.debug("Result query: {}", sql);
        return sql.toString();
    }

    private final Function<FieldName, String> fieldToName = field -> switch (field) {
        case USER_NAME -> mapping.getUsername();
        case NAME -> mapping.getName();
        case SURNAME -> mapping.getSurname();
//        default -> throw new IllegalStateException("Unexpected value: " + field);
    };

    private final FourConsumer<Optional<String>, FieldName,
            List<String>, StringBuilder> extractor = (o, fieldName, paramList, sb) -> {
        if (o.isPresent() && !o.get().isEmpty()) {
            sb.append(fieldName.getCriteria().formatted(fieldToName.apply(fieldName)));
            paramList.add(o.get());
        }
    };
}
