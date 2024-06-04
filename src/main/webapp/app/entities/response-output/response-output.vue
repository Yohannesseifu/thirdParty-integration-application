<template>
  <div>
    <h2 id="page-heading" data-cy="ResponseOutputHeading">
      <span id="response-output-heading">Response Outputs</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh list</span>
        </button>
        <router-link :to="{ name: 'ResponseOutputCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-response-output"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span>Create a new Response Output</span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && responseOutputs && responseOutputs.length === 0">
      <span>No Response Outputs found</span>
    </div>
    <div class="table-responsive" v-if="responseOutputs && responseOutputs.length > 0">
      <table class="table table-striped" aria-describedby="responseOutputs">
        <thead>
          <tr>
            <th scope="row"><span>ID</span></th>
            <th scope="row"><span>Output Name</span></th>
            <th scope="row"><span>Data Type</span></th>
            <th scope="row"><span>Response Value Path</span></th>
            <th scope="row"><span>Response Scope</span></th>
            <th scope="row"><span>Core Mapping</span></th>
            <th scope="row"><span>Expected Value</span></th>
            <th scope="row"><span>Operation</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="responseOutput in responseOutputs" :key="responseOutput.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ResponseOutputView', params: { responseOutputId: responseOutput.id } }">{{
                responseOutput.id
              }}</router-link>
            </td>
            <td>{{ responseOutput.outputName }}</td>
            <td>{{ responseOutput.dataType }}</td>
            <td>{{ responseOutput.responseValuePath }}</td>
            <td>{{ responseOutput.responseScope }}</td>
            <td>{{ responseOutput.coreMapping }}</td>
            <td>{{ responseOutput.expectedValue }}</td>
            <td>
              <div v-if="responseOutput.operation">
                <router-link :to="{ name: 'OperationView', params: { operationId: responseOutput.operation.id } }">{{
                  responseOutput.operation.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'ResponseOutputView', params: { responseOutputId: responseOutput.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'ResponseOutputEdit', params: { responseOutputId: responseOutput.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(responseOutput)"
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
        <span id="thirdPartyIntegrationApplicationApp.responseOutput.delete.question" data-cy="responseOutputDeleteDialogHeading"
          >Confirm delete operation</span
        >
      </template>
      <div class="modal-body">
        <p id="jhi-delete-responseOutput-heading">Are you sure you want to delete Response Output {{ removeId }}?</p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-responseOutput"
            data-cy="entityConfirmDeleteButton"
            v-on:click="removeResponseOutput()"
          >
            Delete
          </button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./response-output.component.ts"></script>
