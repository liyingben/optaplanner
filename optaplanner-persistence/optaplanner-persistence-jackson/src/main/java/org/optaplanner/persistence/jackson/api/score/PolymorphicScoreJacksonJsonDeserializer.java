/*
 * Copyright 2018 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.optaplanner.persistence.jackson.api.score;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.optaplanner.core.api.score.Score;
import org.optaplanner.core.api.score.buildin.bendable.BendableScore;
import org.optaplanner.core.api.score.buildin.bendablebigdecimal.BendableBigDecimalScore;
import org.optaplanner.core.api.score.buildin.bendablelong.BendableLongScore;
import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;
import org.optaplanner.core.api.score.buildin.hardmediumsoftlong.HardMediumSoftLongScore;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.buildin.hardsoftbigdecimal.HardSoftBigDecimalScore;
import org.optaplanner.core.api.score.buildin.hardsoftdouble.HardSoftDoubleScore;
import org.optaplanner.core.api.score.buildin.hardsoftlong.HardSoftLongScore;
import org.optaplanner.core.api.score.buildin.simple.SimpleScore;
import org.optaplanner.core.api.score.buildin.simplebigdecimal.SimpleBigDecimalScore;
import org.optaplanner.core.api.score.buildin.simpledouble.SimpleDoubleScore;
import org.optaplanner.core.api.score.buildin.simplelong.SimpleLongScore;
import org.optaplanner.core.api.score.constraint.ConstraintMatch;
import org.optaplanner.core.impl.score.definition.ScoreDefinition;

/**
 * {@inheritDoc}
 */
public class PolymorphicScoreJacksonJsonDeserializer extends JsonDeserializer<Score> {

    @Override
    public Score deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        JsonNode node = parser.getCodec().readTree(parser);
        Iterator<Map.Entry<String, JsonNode>> iterator = node.fields();
        if (!iterator.hasNext()) {
            throw new IllegalStateException("A polymorphic score field's JSON node (" + node.toString()
                    + ") doesn't have any field.");
        }
        Map.Entry<String, JsonNode> entry = iterator.next();
        if (iterator.hasNext()) {
            throw new IllegalStateException("A polymorphic score field's JSON node (" + node.toString()
                    + ") has multiple fields.");
        }
        String scoreClassSimpleName = entry.getKey();
        String scoreString = entry.getValue().asText();
        if (scoreClassSimpleName.equals(SimpleScore.class.getSimpleName())) {
            return SimpleScore.parseScore(scoreString);
        } else if (scoreClassSimpleName.equals(SimpleLongScore.class.getSimpleName())) {
            return SimpleLongScore.parseScore(scoreString);
        } else if (scoreClassSimpleName.equals(SimpleDoubleScore.class.getSimpleName())) {
            return SimpleDoubleScore.parseScore(scoreString);
        } else if (scoreClassSimpleName.equals(SimpleBigDecimalScore.class.getSimpleName())) {
            return SimpleBigDecimalScore.parseScore(scoreString);
        } else if (scoreClassSimpleName.equals(HardSoftScore.class.getSimpleName())) {
            return HardSoftScore.parseScore(scoreString);
        } else if (scoreClassSimpleName.equals(HardSoftLongScore.class.getSimpleName())) {
            return HardSoftLongScore.parseScore(scoreString);
        } else if (scoreClassSimpleName.equals(HardSoftDoubleScore.class.getSimpleName())) {
            return HardSoftDoubleScore.parseScore(scoreString);
        } else if (scoreClassSimpleName.equals(HardSoftBigDecimalScore.class.getSimpleName())) {
            return HardSoftBigDecimalScore.parseScore(scoreString);
        } else if (scoreClassSimpleName.equals(HardMediumSoftScore.class.getSimpleName())) {
            return HardMediumSoftScore.parseScore(scoreString);
        } else if (scoreClassSimpleName.equals(HardMediumSoftLongScore.class.getSimpleName())) {
            return HardMediumSoftLongScore.parseScore(scoreString);
        } else if (scoreClassSimpleName.equals(BendableScore.class.getSimpleName())) {
            return BendableScore.parseScore(scoreString);
        } else if (scoreClassSimpleName.equals(BendableLongScore.class.getSimpleName())) {
            return BendableLongScore.parseScore(scoreString);
        } else if (scoreClassSimpleName.equals(BendableBigDecimalScore.class.getSimpleName())) {
            return BendableBigDecimalScore.parseScore(scoreString);
        } else {
            throw new IllegalArgumentException("Unrecognized scoreClassSimpleName (" + scoreClassSimpleName
                    + ") for scoreString (" + scoreString + ").");
        }
    }

}