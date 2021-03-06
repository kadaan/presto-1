/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.facebook.presto.sql.tree;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class LikePredicate
        extends Expression
{
    private final Expression value;
    private final Expression pattern;
    private final Expression escape;

    public LikePredicate(Expression value, Expression pattern, Expression escape)
    {
        this(Optional.empty(), value, pattern, escape);
    }

    public LikePredicate(NodeLocation location, Expression value, Expression pattern, Expression escape)
    {
        this(Optional.of(location), value, pattern, escape);
    }

    private LikePredicate(Optional<NodeLocation> location, Expression value, Expression pattern, Expression escape)
    {
        super(location);
        requireNonNull(value, "value is null");
        requireNonNull(pattern, "pattern is null");

        this.value = value;
        this.pattern = pattern;
        this.escape = escape;
    }

    public Expression getValue()
    {
        return value;
    }

    public Expression getPattern()
    {
        return pattern;
    }

    public Expression getEscape()
    {
        return escape;
    }

    @Override
    public <R, C> R accept(AstVisitor<R, C> visitor, C context)
    {
        return visitor.visitLikePredicate(this, context);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LikePredicate that = (LikePredicate) o;

        if (escape != null ? !escape.equals(that.escape) : that.escape != null) {
            return false;
        }
        if (!pattern.equals(that.pattern)) {
            return false;
        }
        if (!value.equals(that.value)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = value.hashCode();
        result = 31 * result + pattern.hashCode();
        result = 31 * result + (escape != null ? escape.hashCode() : 0);
        return result;
    }
}
