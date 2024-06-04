<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="integrationFieldMapping">
        <h2 class="jh-entity-heading" data-cy="integrationFieldMappingDetailsHeading">
          <span>Integration Field Mapping</span> {{ integrationFieldMapping.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Integration Operation</span>
          </dt>
          <dd>
            <div v-if="integrationFieldMapping.integrationOperation">
              <router-link
                :to="{
                  name: 'IntegrationOperationView',
                  params: { integrationOperationId: integrationFieldMapping.integrationOperation.id },
                }"
                >{{ integrationFieldMapping.integrationOperation.id }}</router-link
              >
            </div>
          </dd>
          <dt>
            <span>Field</span>
          </dt>
          <dd>
            <div v-if="integrationFieldMapping.field">
              <router-link :to="{ name: 'FieldView', params: { fieldId: integrationFieldMapping.field.id } }">{{
                integrationFieldMapping.field.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span>Request Input</span>
          </dt>
          <dd>
            <div v-if="integrationFieldMapping.requestInput">
              <router-link :to="{ name: 'RequestInputView', params: { requestInputId: integrationFieldMapping.requestInput.id } }">{{
                integrationFieldMapping.requestInput.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span>Back</span>
        </button>
        <router-link
          v-if="integrationFieldMapping.id"
          :to="{ name: 'IntegrationFieldMappingEdit', params: { integrationFieldMappingId: integrationFieldMapping.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span>Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./integration-field-mapping-details.component.ts"></script>
