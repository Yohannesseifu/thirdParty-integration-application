<template>
  <div>
    <h2 id="page-heading" data-cy="IntegrationFieldMappingHeading">
      <span id="integration-field-mapping-heading">Integration Field Mappings</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh list</span>
        </button>
        <router-link :to="{ name: 'IntegrationFieldMappingCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-integration-field-mapping"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span>Create a new Integration Field Mapping</span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && integrationFieldMappings && integrationFieldMappings.length === 0">
      <span>No Integration Field Mappings found</span>
    </div>
    <div class="table-responsive" v-if="integrationFieldMappings && integrationFieldMappings.length > 0">
      <table class="table table-striped" aria-describedby="integrationFieldMappings">
        <thead>
          <tr>
            <th scope="row"><span>ID</span></th>
            <th scope="row"><span>Integration Operation</span></th>
            <th scope="row"><span>Field</span></th>
            <th scope="row"><span>Request Input</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="integrationFieldMapping in integrationFieldMappings" :key="integrationFieldMapping.id" data-cy="entityTable">
            <td>
              <router-link
                :to="{ name: 'IntegrationFieldMappingView', params: { integrationFieldMappingId: integrationFieldMapping.id } }"
                >{{ integrationFieldMapping.id }}</router-link
              >
            </td>
            <td>
              <div v-if="integrationFieldMapping.integrationOperation">
                <router-link
                  :to="{
                    name: 'IntegrationOperationView',
                    params: { integrationOperationId: integrationFieldMapping.integrationOperation.id },
                  }"
                  >{{ integrationFieldMapping.integrationOperation.id }}</router-link
                >
              </div>
            </td>
            <td>
              <div v-if="integrationFieldMapping.field">
                <router-link :to="{ name: 'FieldView', params: { fieldId: integrationFieldMapping.field.id } }">{{
                  integrationFieldMapping.field.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="integrationFieldMapping.requestInput">
                <router-link :to="{ name: 'RequestInputView', params: { requestInputId: integrationFieldMapping.requestInput.id } }">{{
                  integrationFieldMapping.requestInput.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'IntegrationFieldMappingView', params: { integrationFieldMappingId: integrationFieldMapping.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'IntegrationFieldMappingEdit', params: { integrationFieldMappingId: integrationFieldMapping.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(integrationFieldMapping)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <template #modal-title>
        <span
          id="thirdPartyIntegrationApplicationApp.integrationFieldMapping.delete.question"
          data-cy="integrationFieldMappingDeleteDialogHeading"
          >Confirm delete operation</span
        >
      </template>
      <div class="modal-body">
        <p id="jhi-delete-integrationFieldMapping-heading">Are you sure you want to delete Integration Field Mapping {{ removeId }}?</p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-integrationFieldMapping"
            data-cy="entityConfirmDeleteButton"
            v-on:click="removeIntegrationFieldMapping()"
          >
            Delete
          </button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./integration-field-mapping.component.ts"></script>
