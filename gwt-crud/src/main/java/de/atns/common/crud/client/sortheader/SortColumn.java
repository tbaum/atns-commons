package de.atns.common.crud.client.sortheader;

public class SortColumn<FIELD extends OrderField> {

    public final FIELD field;
    public final OrderField.Sort value;

    public SortColumn(final FIELD field, final OrderField.Sort value) {
        this.field = field;
        this.value = value;
    }

    @Override public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final SortColumn that = (SortColumn) o;

        if (field != null ? !field.equals(that.field) : that.field != null) return false;
        if (value != that.value) return false;

        return true;
    }

    @Override public int hashCode() {
        int result = field != null ? field.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
