package de.atns.common.crud.client.sortheader;

public class SortColumn<FIELD extends OrderField> {
// ------------------------------ FIELDS ------------------------------

    public final FIELD field;
    public final OrderField.Sort value;

// --------------------------- CONSTRUCTORS ---------------------------

    SortColumn(FIELD field, OrderField.Sort value) {
        this.field = field;
        this.value = value;
    }

// ------------------------ CANONICAL METHODS ------------------------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SortColumn that = (SortColumn) o;

        if (field != null ? !field.equals(that.field) : that.field != null) return false;
        if (value != that.value) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = field != null ? field.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
