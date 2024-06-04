<template>
  <div>
    <h2 id="page-heading" data-cy="ApiIntegrationHeading">
      <span id="api-integration-heading">Api Integrations</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh list</span>
        </button>
        <router-link :to="{ name: 'ApiIntegrationCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-api-integration"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span>Create a new Api Integration</span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && apiIntegrations && apiIntegrations.length === 0">
      <span>No Api Integrations found</span>
    </div>
    <div class="table-responsive" v-if="apiIntegrations && apiIntegrations.length > 0">
      <table class="table table-striped" aria-describedby="apiIntegrations">
        <thead>
          <tr>
            <th scope="row"><span>ID</span></th>
            <th scope="row"><span>Name</span></th>
            <th scope="row"><span>Url</span></th>
            <th scope="row"><span>Type</span></th>
            <th scope="row"><span>Auth</span></th>
            <th scope="row"><span>Description</span></th>
            <th scope="row"><span>Version</span></th>
            <th scope="row"><span>Timeout</span></th>
            <th scope="row"><span>Retry Retries</span></th>
            <th scope="row"><span>Retry Delay</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="apiIntegration in apiIntegrations" :key="apiIntegration.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ApiIntegrationView', params: { apiIntegrationId: apiIntegration.id } }">{{
                apiIntegration.id
              }}</router-link>
            </td>
            <td>{{ apiIntegration.name }}</td>
            <td>{{ apiIntegration.url }}</td>
            <td>{{ apiIntegration.type }}</td>
            <td>{{ apiIntegration.auth }}</td>
            <td>{{ apiIntegration.description }}</td>
            <td>{{ apiIntegration.version }}</td>
            <td>{{ apiIntegration.timeout }}</td>
            <td>{{ apiIntegration.retryRetries }}</td>
            <td>{{ apiIntegration.retryDelay }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'ApiIntegrationView', params: { apiIntegrationId: apiIntegration.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'ApiIntegrationEdit', params: { apiIntegrationId: apiIntegration.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(apiIntegration)"
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
        <span id="thirdPartyIntegrationApplicationApp.apiIntegration.delete.question" data-cy="apiIntegrationDeleteDialogHeading"
          >Confirm delete operation</span
        >
      </template>
      <div class="modal-body">
        <p id="jhi-delete-apiIntegration-heading">Are you sure you want to delete Api Integration {{ removeId }}?</p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-apiIntegration"
            data-cy="entityConfirmDeleteButton"
            v-on:click="removeApiIntegration()"
          >
            Delete
          </button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./api-integration.component.ts"></script>
