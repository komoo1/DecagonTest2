package com.decagon.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author Victor.Komolafe
 */

@NoRepositoryBean
interface _BaseRepository<T, ID> extends JpaRepository<T, ID> {

    // TODO expose methods selectively
}
