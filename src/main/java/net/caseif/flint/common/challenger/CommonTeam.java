/*
 * New BSD License (BSD-new)
 *
 * Copyright (c) 2015 Maxim Roncacé
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     - Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     - Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     - Neither the name of the copyright holder nor the names of its contributors
 *       may be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.caseif.flint.common.challenger;

import static com.google.common.base.Preconditions.checkArgument;

import net.caseif.flint.challenger.Challenger;
import net.caseif.flint.challenger.Team;
import net.caseif.flint.common.metadata.CommonMetadatable;
import net.caseif.flint.exception.OrphanedObjectException;
import net.caseif.flint.minigame.Minigame;
import net.caseif.flint.round.Round;

import com.google.common.collect.ImmutableSet;

import java.util.HashSet;
import java.util.Set;

/**
 * Implements {@link Team}.
 *
 * @author Max Roncacé
 */
public class CommonTeam extends CommonMetadatable implements Team {

    protected String id;
    protected Round round;

    protected String name;
    protected Set<Challenger> challengers = new HashSet<>();

    public CommonTeam(String id, Round round) throws IllegalArgumentException {
        assert id != null;
        assert round != null;
        if (round.getTeam(id).isPresent()) {
            throw new IllegalArgumentException("Team \"" + id + "\" already exists");
        }
        this.id = id;
        this.name = id;
        this.round = round;
    }

    @Override
    public String getId() throws OrphanedObjectException {
        checkState();
        return id;
    }

    @Override
    public String getName() throws OrphanedObjectException {
        checkState();
        return name;
    }

    @Override
    public void setName(String name) throws OrphanedObjectException {
        checkState();
        this.name = name;
    }

    @Override
    public Round getRound() throws OrphanedObjectException {
        checkState();
        return round;
    }

    @Override
    public ImmutableSet<Challenger> getChallengers() throws OrphanedObjectException {
        checkState();
        return ImmutableSet.copyOf(challengers);
    }

    @Override
    public void addChallenger(Challenger challenger) throws IllegalArgumentException, OrphanedObjectException {
        checkState();
        checkArgument(challenger.getRound() == getRound(),
                "Cannot add challenger to team: round mismatch");
        challengers.add(challenger);
    }

    @Override
    public void removeChallenger(Challenger challenger) throws IllegalArgumentException, OrphanedObjectException {
        checkState();
        checkArgument(challengers.contains(challenger), "Cannot remove challenger from team: not present");
        challengers.remove(challenger);
    }

    @Override
    public Minigame getMinigame() throws OrphanedObjectException {
        checkState();
        return getRound().getMinigame();
    }

    @Override
    public String getPlugin() throws OrphanedObjectException {
        checkState();
        return getRound().getPlugin();
    }

    /**
     * Checks the state of this object.
     *
     * @throws OrphanedObjectException If this object is orphaned
     */
    protected void checkState() throws OrphanedObjectException {
        if (round == null) {
            throw new OrphanedObjectException(this);
        }
    }

    /**
     * Orphans this object.
     */
    public void orphan() {
        round = null;
    }

}
