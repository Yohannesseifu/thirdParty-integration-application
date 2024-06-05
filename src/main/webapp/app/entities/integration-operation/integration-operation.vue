<template>
  <div>
    <h2 id="page-heading" data-cy="IntegrationOperationHeading">
      <span id="integration-operation-heading">Integration Operations</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh list</span>
        </button>
        <router-link :to="{ name: 'IntegrationOperationCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-integration-operation"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span>Create a new Integration Operation</span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && integrationOperations && integrationOperations.length === 0">
      <span>No Integration Operations found</span>
    </div>
    <div class="table-responsive" v-if="integrationOperations && integrationOperations.length > 0">
      <table class="table table-striped" aria-describedby="integrationOperations">
        <thead>
          <tr>
            <th scope="row"><span>ID</span></th>
            <th scope="row"><span>Operation Type</span></th>
            <th scope="row"><span>Third Party Integration</span></th>
            <th scope="row"><span>Operation</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="integrationOperation in integrationOperations" :key="integrationOperation.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'IntegrationOperationView', params: { integrationOperationId: integrationOperation.id } }">{{
                integrationOperation.id
              }}</router-link>
            </td>
            <td>{{ integrationOperation.operationType }}</td>
            <td>
              <div v-if="integrationOperation.thirdPartyIntegration">
                <router-link
                  :to="{
                    name: 'ThirdPartyIntegrationView',
                    params: { thirdPartyIntegrationId: integrationOperation.thirdPartyIntegration.id },
                  }"
                  >{{ integrationOperation.thirdPartyIntegration.id }}</router-link
                >
              </div>
            </td>
            <td>
              <div v-if="integrationOperation.operation">
                <router-link :to="{ name: 'OperationView', params: { operationId: integrationOperation.operation.id } }">{{
                  integrationOperation.operation.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'IntegrationOperationView', params: { integrationOperationId: integrationOperation.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'IntegrationOperationEdit', params: { integrationOperationId: integrationOperation.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(integrationOperation)"
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
          id="thirdPartyIntegrationApplicationApp.integrationOperation.delete.question"
          data-cy="integrationOperationDeleteDialogHeading"
          >Confirm delete operation</span
        >
      </template>
      <div class="modal-body">
        <p id="jhi-delete-integrationOperation-heading">Are you sure you want to delete Integration Operation {{ removeId }}?</p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-integrationOperation"
            data-cy="entityConfirmDeleteButton"
            v-on:click="removeIntegrationOperation()"
          >
            Delete
          </button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./integration-operation.component.ts"></script>
