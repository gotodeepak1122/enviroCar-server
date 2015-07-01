/*
 * Copyright (C) 2013 The enviroCar project
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.envirocar.server.etl.dataSetDump.POJOEntities;

import com.vividsolutions.jts.geom.Geometry;
import org.envirocar.server.core.entities.Gender;
import org.envirocar.server.core.entities.User;
import org.joda.time.DateTime;

import java.net.URL;
import java.util.Set;

/**
 * @author deepaknair on 17/06/15 AD.
 */
public class UserPOJO implements User {
    @Override
    public Set<String> getBadges() {
        return null;
    }

    @Override
    public boolean hasBadges() {
        return false;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public boolean hasName() {
        return false;
    }

    @Override
    public String getMail() {
        return null;
    }

    @Override
    public void setMail(String mail) {

    }

    @Override
    public boolean hasMail() {
        return false;
    }

    @Override
    public String getToken() {
        return null;
    }

    @Override
    public void setToken(String token) {

    }

    @Override
    public boolean hasToken() {
        return false;
    }

    @Override
    public boolean isAdmin() {
        return false;
    }

    @Override
    public void setAdmin(boolean isAdmin) {

    }

    @Override
    public String getCountry() {
        return null;
    }

    @Override
    public void setCountry(String country) {

    }

    @Override
    public boolean hasCountry() {
        return false;
    }

    @Override
    public String getDayOfBirth() {
        return null;
    }

    @Override
    public void setDayOfBirth(String dayOfBirth) {

    }

    @Override
    public boolean hasDayOfBirth() {
        return false;
    }

    @Override
    public String getFirstName() {
        return null;
    }

    @Override
    public void setFirstName(String firstName) {

    }

    @Override
    public boolean hasFirstName() {
        return false;
    }

    @Override
    public Gender getGender() {
        return null;
    }

    @Override
    public void setGender(Gender gender) {

    }

    @Override
    public boolean hasGender() {
        return false;
    }

    @Override
    public String getLanguage() {
        return null;
    }

    @Override
    public void setLanguage(String language) {

    }

    @Override
    public boolean hasLanguage() {
        return false;
    }

    @Override
    public String getLastName() {
        return null;
    }

    @Override
    public void setLastName(String lastName) {

    }

    @Override
    public boolean hasLastName() {
        return false;
    }

    @Override
    public Geometry getLocation() {
        return null;
    }

    @Override
    public void setLocation(Geometry location) {

    }

    @Override
    public boolean hasLocation() {
        return false;
    }

    @Override
    public URL getUrl() {
        return null;
    }

    @Override
    public void setUrl(URL url) {

    }

    @Override
    public boolean hasUrl() {
        return false;
    }

    @Override
    public String getAboutMe() {
        return null;
    }

    @Override
    public void setAboutMe(String aboutMe) {

    }

    @Override
    public boolean hasAboutMe() {
        return false;
    }

    @Override
    public String getTermsOfUseVersion() {
        return null;
    }

    @Override
    public void setTermsOfUseVersion(String tou) {

    }

    @Override
    public boolean hasAcceptedTermsOfUseVersion() {
        return false;
    }

    @Override
    public DateTime getCreationTime() {
        return null;
    }

    @Override
    public boolean hasCreationTime() {
        return false;
    }

    @Override
    public DateTime getModificationTime() {
        return null;
    }

    @Override
    public boolean hasModificationTime() {
        return false;
    }
}
