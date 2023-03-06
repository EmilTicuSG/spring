/*
 * This file is generated by jOOQ.
 */
package victor.training.spring.jooq.table.tables;


import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row2;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import victor.training.spring.jooq.table.Keys;
import victor.training.spring.jooq.table.Public;
import victor.training.spring.jooq.table.tables.records.BookRecord;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Book extends TableImpl<BookRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>PUBLIC.BOOK</code>
     */
    public static final Book BOOK = new Book();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<BookRecord> getRecordType() {
        return BookRecord.class;
    }

    /**
     * The column <code>PUBLIC.BOOK.ID</code>.
     */
    public final TableField<BookRecord, Integer> ID = createField(DSL.name("ID"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>PUBLIC.BOOK.TITLE</code>.
     */
    public final TableField<BookRecord, String> TITLE = createField(DSL.name("TITLE"), SQLDataType.VARCHAR(100).nullable(false), this, "");

    private Book(Name alias, Table<BookRecord> aliased) {
        this(alias, aliased, null);
    }

    private Book(Name alias, Table<BookRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>PUBLIC.BOOK</code> table reference
     */
    public Book(String alias) {
        this(DSL.name(alias), BOOK);
    }

    /**
     * Create an aliased <code>PUBLIC.BOOK</code> table reference
     */
    public Book(Name alias) {
        this(alias, BOOK);
    }

    /**
     * Create a <code>PUBLIC.BOOK</code> table reference
     */
    public Book() {
        this(DSL.name("BOOK"), null);
    }

    public <O extends Record> Book(Table<O> child, ForeignKey<O, BookRecord> key) {
        super(child, key, BOOK);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<BookRecord> getPrimaryKey() {
        return Keys.CONSTRAINT_1;
    }

    @Override
    public Book as(String alias) {
        return new Book(DSL.name(alias), this);
    }

    @Override
    public Book as(Name alias) {
        return new Book(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Book rename(String name) {
        return new Book(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Book rename(Name name) {
        return new Book(name, null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row2<Integer, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }
}
