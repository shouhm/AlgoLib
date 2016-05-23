package structure;

public class SegmentTree {
    static abstract public class RangeOp {
        abstract public RangeOp operPlus(RangeOp op);
    }
    static abstract public class RangeValue {
        abstract public RangeValue operOnValue(RangeOp op);
        abstract public RangeValue valuePlus(RangeValue value);
    }
}
