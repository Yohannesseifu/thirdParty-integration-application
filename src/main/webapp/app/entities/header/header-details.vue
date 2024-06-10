<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="header">
        <h2 class="jh-entity-heading" data-cy="headerDetailsHeading"><span>Header</span> {{ header.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Key</span>
          </dt>
          <dd>
            <span>{{ header.key }}</span>
          </dd>
          <dt>
            <span>Value</span>
          </dt>
          <dd>
            <span>{{ header.value }}</span>
          </dd>
          <dt>
            <span>Api Request</span>
          </dt>
          <dd>
            <div v-if="header.apiRequest">
              <router-link :to="{ name: 'ApiRequestView', params: { apiRequestId: header.apiRequest.id } }">{{
                header.apiRequest.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span>Back</span>
        </button>
        <router-link v-if="header.id" :to="{ name: 'HeaderEdit', params: { headerId: header.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span>Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./header-details.component.ts"></script>
