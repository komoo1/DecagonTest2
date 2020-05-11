package com.decagon.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Victor.Komolafe
 */

//@NoRepositoryBean
interface _BaseRepository<T, ID> extends JpaRepository<T, ID> {

    // TODO expose methods selectively
}
